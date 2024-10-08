/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.logsdb.datageneration.fields;

import org.elasticsearch.logsdb.datageneration.FieldDataGenerator;
import org.elasticsearch.logsdb.datageneration.FieldType;
import org.elasticsearch.logsdb.datageneration.datasource.DataSource;

public interface PredefinedField {
    String name();

    FieldDataGenerator generator(DataSource dataSource);

    record WithType(String fieldName, FieldType fieldType) implements PredefinedField {
        @Override
        public String name() {
            return fieldName;
        }

        @Override
        public FieldDataGenerator generator(DataSource dataSource) {
            return fieldType().generator(fieldName, dataSource);
        }
    }

    record WithGenerator(String fieldName, FieldDataGenerator generator) implements PredefinedField {
        @Override
        public String name() {
            return fieldName;
        }

        @Override
        public FieldDataGenerator generator(DataSource dataSource) {
            return generator;
        }
    }
}
