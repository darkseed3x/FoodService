package ru.vvzl.fs.rs.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.vvzl.fs.rs.model.AssetResponse;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssetResponseMapper implements RowMapper<AssetResponse> {
    @Override
    public AssetResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setId(rs.getInt("asset_id"));
        assetResponse.setName(rs.getString("name"));
        assetResponse.setDescription(rs.getString("description"));
        assetResponse.setPrice(rs.getBigDecimal("price").setScale(2, RoundingMode.FLOOR));
        return assetResponse;
    }
}
