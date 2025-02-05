package com.dxerp.ebs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ConnectionCallback;

import org.springframework.stereotype.Repository;

import com.dxerp.ebs.dto.CoaDTO;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

@Repository
public class CoaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CoaResponse createCoaHead(String coaName, String coaType, int isGroupHead, String keyWord, 
                                     Long parentId, int sortBy, String groupName, String groupCode, 
                                     String companyCode, int isSpecialGl, String gcBk) {
        return jdbcTemplate.execute((ConnectionCallback<CoaResponse>) connection -> {
            CallableStatement callableStatement = connection.prepareCall(
                "{CALL CREATE_COA_HEAD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"
            );

            callableStatement.setString(1, coaName);
            callableStatement.setString(2, coaType);
            callableStatement.setInt(3, isGroupHead);
            callableStatement.setString(4, keyWord);
            callableStatement.setLong(5, parentId);
            callableStatement.setInt(6, sortBy);
            callableStatement.setString(7, groupName);
            callableStatement.setString(8, groupCode);
            callableStatement.setString(9, companyCode);
            callableStatement.setInt(10, isSpecialGl);
            callableStatement.setString(11, gcBk);
            
            callableStatement.registerOutParameter(12, Types.NUMERIC); // o_coa_id
            callableStatement.registerOutParameter(13, Types.VARCHAR); // o_generated_code

            callableStatement.execute();

            Long generatedId = callableStatement.getLong(12);
            String generatedCode = callableStatement.getString(13);

            return new CoaResponse(generatedId, generatedCode);
        });
    }

    public static class CoaResponse {
        private Long coaId;
        private String coaCode;

        public CoaResponse(Long coaId, String coaCode) {
            this.coaId = coaId;
            this.coaCode = coaCode;
        }

        // Getters and Setters
    }

        public List<CoaDTO> findAll() {
            // Implement the method logic here
        String sql = "SELECT * FROM ACC_COA";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CoaDTO coaDTO = new CoaDTO();
            coaDTO.setId(rs.getLong("id"));
            coaDTO.setCoaName(rs.getString("COA_NAME"));
            coaDTO.setCoaType(rs.getString("COA_TYPE"));
            coaDTO.setGroupHead(rs.getInt("is_group_head"));
            coaDTO.setKeyWord(rs.getString("key_word"));
            coaDTO.setParentId(rs.getLong("parent_acc_coa_id"));
            coaDTO.setCode(rs.getString("code"));
            coaDTO.setGroupCode(rs.getString("group_code"));
            coaDTO.setGroupName(rs.getString("group_name"));
            // Set other fields of CoaDTO as needed
            return coaDTO;
        });
           
        }
}
