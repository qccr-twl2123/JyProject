<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	
	<!-- 新增-->
	<insert id="save" parameterType="pd">
		insert into TB_${objectNameUpper}(
	<#list fieldList as var>
			${var[0]},	
	</#list>
			${objectNameUpper}_ID
		) values (
	<#list fieldListLower as var>
			${r"#{"}${var[0]}${r"}"},	
	</#list>
			${r"#{"}${objectNameLower}_id${r"}"}
		)
	</insert>
	
	
	<!-- 删除-->
	<delete id="delete" parameterType="pd">
		delete from TB_${objectNameUpper}
		where 
			${objectNameUpper}_ID = ${r"#{"}${objectNameLower}_id${r"}"}
	</delete>
	
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update  TB_${objectNameUpper}
			set 
	<#list fieldListLower as var>
		<#if var[3] == "是">
				${var[0]} = ${r"#{"}${var[0]}${r"}"},
		</#if>
	</#list>
			${objectNameUpper}_ID = ${objectNameLower}_id
			where 
				${objectNameUpper}_ID = ${r"#{"}${objectNameLower}_id${r"}"}
	</update>
	
	
	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
	<#list fieldListLower as var>
			${var[0]},	
	</#list>
			${objectNameUpper}_ID
		from 
			TB_${objectNameUpper}
		where 
			${objectNameUpper}_ID = ${r"#{"}${objectNameLower}_id${r"}"}
	</select>
	
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<#list fieldListLower as var>
				a.${var[0]},	
		</#list>
				a.${objectNameUpper}_ID
		from 
				TB_${objectNameUpper} a
		where  1=1
		<if test="pd.content != null and pd.content != ''"><!-- 关键词检索 -->
			and
				(
					 
				)
		</if>
		
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<#list fieldListLower as var>
				a.${var[0]},	
		</#list>
				a.${objectNameLower}_id
		from 
				TB_${objectNameUpper} a
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from TB_${objectNameUpper}
		where 
			${objectNameUpper}_ID in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>