package org.zerock.fc.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;


public enum MyBatisLoader {

    INSTANCE;

    private SqlSessionFactory sqlSessionFactory;

    //enum클래스여서 외부에서 생성자를 만들 수 없기 때문에 MyBatisLoader()로 생성자만들기
    MyBatisLoader(){
        try{
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlSessionFactory getFactory() {
        return this.sqlSessionFactory;
    }

}
