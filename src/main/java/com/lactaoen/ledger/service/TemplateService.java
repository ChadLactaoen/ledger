package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Template;
import com.lactaoen.ledger.model.template.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    private final DynamoDBMapper dynamoDBMapper;

    public TemplateService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Template> getTemplatesByType(Type type) {
        return dynamoDBMapper.query(Template.class, createTypeIndexQuery(type));
    }

    private DynamoDBQueryExpression<Template> createTypeIndexQuery(Type type) {
        return new DynamoDBQueryExpression<Template>()
                .withIndexName("type-templateName-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("#type = :type")
                .withExpressionAttributeNames(ImmutableMap.of("#type", "type"))
                .withExpressionAttributeValues(ImmutableMap.of(":type", new AttributeValue(type.name())));
    }
}
