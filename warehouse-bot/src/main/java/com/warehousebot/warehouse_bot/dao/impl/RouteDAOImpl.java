package com.warehousebot.warehouse_bot.dao.impl;

import com.warehousebot.warehouse_bot.dao.interfaces.RouteDAO;
import com.warehousebot.warehouse_bot.model.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RouteDAOImpl implements RouteDAO {

    private final JdbcTemplate jdbc;

    public RouteDAOImpl(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private final RowMapper<Route> routeMapper = (rs, rowNum) -> {

        Route r = new Route();
        r.setId(rs.getLong("id"));
        r.setOrderId(rs.getLong("order_id"));
        r.setSeq(rs.getInt("seq"));
        r.setX(rs.getInt("x"));
        r.setY(rs.getInt("y"));
        return r;

    };

    @Override
    public void create(Route route){

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO routes(order_id, seq, x, y) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, route.getOrderId());
            ps.setInt(2, route.getSeq());
            ps.setInt(3, route.getX());
            ps.setInt(4, route.getY());
            return ps;
        }, kh);

        route.setId(kh.getKey().longValue());

    }

    @Override
    public List<Route> findByOrderId(Long orderId){
        return jdbc.query("SELECT * FROM routes WHERE order_id = ? ORDER BY seq", routeMapper, orderId);
    }

}
