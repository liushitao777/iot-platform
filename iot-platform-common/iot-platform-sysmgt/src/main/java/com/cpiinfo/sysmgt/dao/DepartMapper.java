package com.cpiinfo.sysmgt.dao;

import com.cpiinfo.sysmgt.dto.DepartTreeDTO;
import com.cpiinfo.sysmgt.entity.Depart;
import com.cpiinfo.sysmgt.entity.DepartExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DepartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    long countByExample(DepartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int deleteByExample(DepartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int insert(Depart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int insertSelective(Depart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    List<Depart> selectByExampleWithRowbounds(DepartExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    List<Depart> selectByExample(DepartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    Depart selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Depart record, @Param("example") DepartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Depart record, @Param("example") DepartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Depart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_department
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Depart record);

    List<DepartTreeDTO> getCategory(@Param("pid")String pid);
}