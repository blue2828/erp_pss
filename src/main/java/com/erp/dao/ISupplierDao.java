package com.erp.dao;

import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.entity.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ISupplierDao {
    @Select("<script>" +
                "SELECT * FROM tb_purchase_supplier where 1 = 1 " +
                "<if test=\"supName != null and supName != \'\'\">" +
                "   and supName like concat('%', #{supName}, '%')" +
                "</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    List<Supplier> queryAllSupplier(@Param("supName") String supName, @Param("pageEntity") PageEntity pageEntity);
    @Select("<script>" +
                "SELECT count(*) as num FROM tb_purchase_supplier where 1 = 1 " +
                "<if test=\"supName != null and supName != \'\'\">" +
                "   and supName like concat('%', #{supName}, '%')" +
                "</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    int countAllSupplier(@Param("supName") String supName, @Param("pageEntity") PageEntity pageEntity);
    @Select ("select * from tb_purchase_supplier where id = #{id}")
    Supplier getSupplierById (@Param("id") int id);
    @Insert("insert into tb_purchase_supplier values(null, #{sup.supName}, #{sup.linkMan}, #{sup.mobile}, #{sup.address}, #{sup.mark})")
    int supAdd(@Param("sup") Supplier supplier);
    @Update("update tb_purchase_supplier set supName = #{sup.supName}, linkMan = #{sup.linkMan}, mobile = #{sup.mobile}, " +
            "address = #{sup.address}, mark = #{sup.mark} where id = #{sup.id}")
    int editSup (@Param("sup") Supplier supplier);
}
