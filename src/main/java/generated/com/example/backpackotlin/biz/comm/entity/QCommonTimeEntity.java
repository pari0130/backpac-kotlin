package com.example.backpackotlin.biz.comm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonTimeEntity is a Querydsl query type for CommonTimeEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QCommonTimeEntity extends EntityPathBase<CommonTimeEntity> {

    private static final long serialVersionUID = -900735758L;

    public static final QCommonTimeEntity commonTimeEntity = new QCommonTimeEntity("commonTimeEntity");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedDate = createDateTime("updatedDate", java.time.LocalDateTime.class);

    public QCommonTimeEntity(String variable) {
        super(CommonTimeEntity.class, forVariable(variable));
    }

    public QCommonTimeEntity(Path<? extends CommonTimeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonTimeEntity(PathMetadata metadata) {
        super(CommonTimeEntity.class, metadata);
    }

}

