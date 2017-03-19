package com.verge.utils;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;

public final class Responses {

    private Responses() {
    }

    public static Response ok() {
        return Response.ok().build();
    }

    public static Response ok(Object dto) {
        return Response.ok(dto).build();
    }

    public static Response badRequest(String message) {
        return Response
                .status(Status.BAD_REQUEST)
                .entity(message)
                .build();
    }

    public static Response created() {
        return Response
                .status(Status.CREATED)
                .build();
    }

    public static Response created(Object dto) {
        return Response
                .status(Status.CREATED)
                .entity(dto)
                .build();
    }

    public static Response notFound() {
        return Response
                .status(Status.NOT_FOUND)
                .build();
    }

    public static Response noContent() {
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    public static Response serverError() {
        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .build();
    }
}
