/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.application.connector.action;

import org.elasticsearch.client.internal.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.Scope;
import org.elasticsearch.rest.ServerlessScope;
import org.elasticsearch.rest.action.RestToXContentListener;
import org.elasticsearch.xpack.application.EnterpriseSearch;

import java.util.List;

import static org.elasticsearch.rest.RestRequest.Method.PUT;

@ServerlessScope(Scope.PUBLIC)
public class RestPutConnectorAction extends BaseRestHandler {

    @Override
    public String getName() {
        return "connector_put_action";
    }

    @Override
    public List<Route> routes() {
        return List.of(
            new Route(PUT, "/" + EnterpriseSearch.CONNECTOR_API_ENDPOINT + "/{connector_id}"),
            new Route(PUT, "/" + EnterpriseSearch.CONNECTOR_API_ENDPOINT)
        );
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) {
        String connectorId = restRequest.param("connector_id");
        PutConnectorAction.Request request;
        // Handle empty REST request body
        if (restRequest.hasContent()) {
            request = PutConnectorAction.Request.fromXContentBytes(connectorId, restRequest.content(), restRequest.getXContentType());
        } else {
            request = new PutConnectorAction.Request(connectorId);
        }
        return channel -> client.execute(
            PutConnectorAction.INSTANCE,
            request,
            new RestToXContentListener<>(channel, ConnectorCreateActionResponse::status, r -> null)
        );
    }
}
