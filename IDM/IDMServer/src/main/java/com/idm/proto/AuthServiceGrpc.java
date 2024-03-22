package com.idm.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: auth.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final String SERVICE_NAME = "com.idm.proto.AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.AuthenticationRequest,
      com.idm.proto.Auth.AuthenticationResponse> getAuthenticateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthenticateUser",
      requestType = com.idm.proto.Auth.AuthenticationRequest.class,
      responseType = com.idm.proto.Auth.AuthenticationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.AuthenticationRequest,
      com.idm.proto.Auth.AuthenticationResponse> getAuthenticateUserMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.AuthenticationRequest, com.idm.proto.Auth.AuthenticationResponse> getAuthenticateUserMethod;
    if ((getAuthenticateUserMethod = AuthServiceGrpc.getAuthenticateUserMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getAuthenticateUserMethod = AuthServiceGrpc.getAuthenticateUserMethod) == null) {
          AuthServiceGrpc.getAuthenticateUserMethod = getAuthenticateUserMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.AuthenticationRequest, com.idm.proto.Auth.AuthenticationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthenticateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.AuthenticationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.AuthenticationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("AuthenticateUser"))
              .build();
        }
      }
    }
    return getAuthenticateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.TokenValidationRequest,
      com.idm.proto.Auth.TokenValidationResponse> getValidateTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateToken",
      requestType = com.idm.proto.Auth.TokenValidationRequest.class,
      responseType = com.idm.proto.Auth.TokenValidationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.TokenValidationRequest,
      com.idm.proto.Auth.TokenValidationResponse> getValidateTokenMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.TokenValidationRequest, com.idm.proto.Auth.TokenValidationResponse> getValidateTokenMethod;
    if ((getValidateTokenMethod = AuthServiceGrpc.getValidateTokenMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getValidateTokenMethod = AuthServiceGrpc.getValidateTokenMethod) == null) {
          AuthServiceGrpc.getValidateTokenMethod = getValidateTokenMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.TokenValidationRequest, com.idm.proto.Auth.TokenValidationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidateToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.TokenValidationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.TokenValidationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("ValidateToken"))
              .build();
        }
      }
    }
    return getValidateTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.DestroyTokenRequest,
      com.idm.proto.Auth.DestroyTokenResponse> getDestroyTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DestroyToken",
      requestType = com.idm.proto.Auth.DestroyTokenRequest.class,
      responseType = com.idm.proto.Auth.DestroyTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.DestroyTokenRequest,
      com.idm.proto.Auth.DestroyTokenResponse> getDestroyTokenMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.DestroyTokenRequest, com.idm.proto.Auth.DestroyTokenResponse> getDestroyTokenMethod;
    if ((getDestroyTokenMethod = AuthServiceGrpc.getDestroyTokenMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getDestroyTokenMethod = AuthServiceGrpc.getDestroyTokenMethod) == null) {
          AuthServiceGrpc.getDestroyTokenMethod = getDestroyTokenMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.DestroyTokenRequest, com.idm.proto.Auth.DestroyTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DestroyToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.DestroyTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.DestroyTokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("DestroyToken"))
              .build();
        }
      }
    }
    return getDestroyTokenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub>() {
        @java.lang.Override
        public AuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceStub(channel, callOptions);
        }
      };
    return AuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub>() {
        @java.lang.Override
        public AuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceBlockingStub(channel, callOptions);
        }
      };
    return AuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub>() {
        @java.lang.Override
        public AuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceFutureStub(channel, callOptions);
        }
      };
    return AuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AuthServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void authenticateUser(com.idm.proto.Auth.AuthenticationRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.AuthenticationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthenticateUserMethod(), responseObserver);
    }

    /**
     */
    public void validateToken(com.idm.proto.Auth.TokenValidationRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.TokenValidationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidateTokenMethod(), responseObserver);
    }

    /**
     */
    public void destroyToken(com.idm.proto.Auth.DestroyTokenRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.DestroyTokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDestroyTokenMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthenticateUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.AuthenticationRequest,
                com.idm.proto.Auth.AuthenticationResponse>(
                  this, METHODID_AUTHENTICATE_USER)))
          .addMethod(
            getValidateTokenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.TokenValidationRequest,
                com.idm.proto.Auth.TokenValidationResponse>(
                  this, METHODID_VALIDATE_TOKEN)))
          .addMethod(
            getDestroyTokenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.DestroyTokenRequest,
                com.idm.proto.Auth.DestroyTokenResponse>(
                  this, METHODID_DESTROY_TOKEN)))
          .build();
    }
  }

  /**
   */
  public static final class AuthServiceStub extends io.grpc.stub.AbstractAsyncStub<AuthServiceStub> {
    private AuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticateUser(com.idm.proto.Auth.AuthenticationRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.AuthenticationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthenticateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validateToken(com.idm.proto.Auth.TokenValidationRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.TokenValidationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void destroyToken(com.idm.proto.Auth.DestroyTokenRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.DestroyTokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDestroyTokenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.idm.proto.Auth.AuthenticationResponse authenticateUser(com.idm.proto.Auth.AuthenticationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthenticateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.idm.proto.Auth.TokenValidationResponse validateToken(com.idm.proto.Auth.TokenValidationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.idm.proto.Auth.DestroyTokenResponse destroyToken(com.idm.proto.Auth.DestroyTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDestroyTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.AuthenticationResponse> authenticateUser(
        com.idm.proto.Auth.AuthenticationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthenticateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.TokenValidationResponse> validateToken(
        com.idm.proto.Auth.TokenValidationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.DestroyTokenResponse> destroyToken(
        com.idm.proto.Auth.DestroyTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDestroyTokenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE_USER = 0;
  private static final int METHODID_VALIDATE_TOKEN = 1;
  private static final int METHODID_DESTROY_TOKEN = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE_USER:
          serviceImpl.authenticateUser((com.idm.proto.Auth.AuthenticationRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.AuthenticationResponse>) responseObserver);
          break;
        case METHODID_VALIDATE_TOKEN:
          serviceImpl.validateToken((com.idm.proto.Auth.TokenValidationRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.TokenValidationResponse>) responseObserver);
          break;
        case METHODID_DESTROY_TOKEN:
          serviceImpl.destroyToken((com.idm.proto.Auth.DestroyTokenRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.DestroyTokenResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.idm.proto.Auth.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getAuthenticateUserMethod())
              .addMethod(getValidateTokenMethod())
              .addMethod(getDestroyTokenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
