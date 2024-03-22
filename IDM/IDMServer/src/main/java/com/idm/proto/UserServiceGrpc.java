package com.idm.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: auth.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final String SERVICE_NAME = "com.idm.proto.UserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.GetUserRequest,
      com.idm.proto.Auth.GetUserResponse> getGetUserByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserById",
      requestType = com.idm.proto.Auth.GetUserRequest.class,
      responseType = com.idm.proto.Auth.GetUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.GetUserRequest,
      com.idm.proto.Auth.GetUserResponse> getGetUserByIdMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.GetUserRequest, com.idm.proto.Auth.GetUserResponse> getGetUserByIdMethod;
    if ((getGetUserByIdMethod = UserServiceGrpc.getGetUserByIdMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUserByIdMethod = UserServiceGrpc.getGetUserByIdMethod) == null) {
          UserServiceGrpc.getGetUserByIdMethod = getGetUserByIdMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.GetUserRequest, com.idm.proto.Auth.GetUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getUserById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.GetUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.GetUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("getUserById"))
              .build();
        }
      }
    }
    return getGetUserByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.CreateUserRequest,
      com.idm.proto.Auth.CreateUserResponse> getCreateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createUser",
      requestType = com.idm.proto.Auth.CreateUserRequest.class,
      responseType = com.idm.proto.Auth.CreateUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.CreateUserRequest,
      com.idm.proto.Auth.CreateUserResponse> getCreateUserMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.CreateUserRequest, com.idm.proto.Auth.CreateUserResponse> getCreateUserMethod;
    if ((getCreateUserMethod = UserServiceGrpc.getCreateUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getCreateUserMethod = UserServiceGrpc.getCreateUserMethod) == null) {
          UserServiceGrpc.getCreateUserMethod = getCreateUserMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.CreateUserRequest, com.idm.proto.Auth.CreateUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.CreateUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.CreateUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("createUser"))
              .build();
        }
      }
    }
    return getCreateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.EditUserRequest,
      com.idm.proto.Auth.EditUserResponse> getEditUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "editUser",
      requestType = com.idm.proto.Auth.EditUserRequest.class,
      responseType = com.idm.proto.Auth.EditUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.EditUserRequest,
      com.idm.proto.Auth.EditUserResponse> getEditUserMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.EditUserRequest, com.idm.proto.Auth.EditUserResponse> getEditUserMethod;
    if ((getEditUserMethod = UserServiceGrpc.getEditUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getEditUserMethod = UserServiceGrpc.getEditUserMethod) == null) {
          UserServiceGrpc.getEditUserMethod = getEditUserMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.EditUserRequest, com.idm.proto.Auth.EditUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "editUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.EditUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.EditUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("editUser"))
              .build();
        }
      }
    }
    return getEditUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.idm.proto.Auth.DeleteUserRequest,
      com.idm.proto.Auth.DeleteUserResponse> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteUser",
      requestType = com.idm.proto.Auth.DeleteUserRequest.class,
      responseType = com.idm.proto.Auth.DeleteUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.idm.proto.Auth.DeleteUserRequest,
      com.idm.proto.Auth.DeleteUserResponse> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<com.idm.proto.Auth.DeleteUserRequest, com.idm.proto.Auth.DeleteUserResponse> getDeleteUserMethod;
    if ((getDeleteUserMethod = UserServiceGrpc.getDeleteUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getDeleteUserMethod = UserServiceGrpc.getDeleteUserMethod) == null) {
          UserServiceGrpc.getDeleteUserMethod = getDeleteUserMethod =
              io.grpc.MethodDescriptor.<com.idm.proto.Auth.DeleteUserRequest, com.idm.proto.Auth.DeleteUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "deleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.DeleteUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.idm.proto.Auth.DeleteUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("deleteUser"))
              .build();
        }
      }
    }
    return getDeleteUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceStub>() {
        @java.lang.Override
        public UserServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceStub(channel, callOptions);
        }
      };
    return UserServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub>() {
        @java.lang.Override
        public UserServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceBlockingStub(channel, callOptions);
        }
      };
    return UserServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub>() {
        @java.lang.Override
        public UserServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceFutureStub(channel, callOptions);
        }
      };
    return UserServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UserServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUserById(com.idm.proto.Auth.GetUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.GetUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserByIdMethod(), responseObserver);
    }

    /**
     */
    public void createUser(com.idm.proto.Auth.CreateUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.CreateUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateUserMethod(), responseObserver);
    }

    /**
     */
    public void editUser(com.idm.proto.Auth.EditUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.EditUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEditUserMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(com.idm.proto.Auth.DeleteUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.DeleteUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUserByIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.GetUserRequest,
                com.idm.proto.Auth.GetUserResponse>(
                  this, METHODID_GET_USER_BY_ID)))
          .addMethod(
            getCreateUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.CreateUserRequest,
                com.idm.proto.Auth.CreateUserResponse>(
                  this, METHODID_CREATE_USER)))
          .addMethod(
            getEditUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.EditUserRequest,
                com.idm.proto.Auth.EditUserResponse>(
                  this, METHODID_EDIT_USER)))
          .addMethod(
            getDeleteUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.idm.proto.Auth.DeleteUserRequest,
                com.idm.proto.Auth.DeleteUserResponse>(
                  this, METHODID_DELETE_USER)))
          .build();
    }
  }

  /**
   */
  public static final class UserServiceStub extends io.grpc.stub.AbstractAsyncStub<UserServiceStub> {
    private UserServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     */
    public void getUserById(com.idm.proto.Auth.GetUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.GetUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createUser(com.idm.proto.Auth.CreateUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.CreateUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void editUser(com.idm.proto.Auth.EditUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.EditUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEditUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(com.idm.proto.Auth.DeleteUserRequest request,
        io.grpc.stub.StreamObserver<com.idm.proto.Auth.DeleteUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.idm.proto.Auth.GetUserResponse getUserById(com.idm.proto.Auth.GetUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.idm.proto.Auth.CreateUserResponse createUser(com.idm.proto.Auth.CreateUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.idm.proto.Auth.EditUserResponse editUser(com.idm.proto.Auth.EditUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEditUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.idm.proto.Auth.DeleteUserResponse deleteUser(com.idm.proto.Auth.DeleteUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UserServiceFutureStub> {
    private UserServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.GetUserResponse> getUserById(
        com.idm.proto.Auth.GetUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.CreateUserResponse> createUser(
        com.idm.proto.Auth.CreateUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.EditUserResponse> editUser(
        com.idm.proto.Auth.EditUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEditUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.idm.proto.Auth.DeleteUserResponse> deleteUser(
        com.idm.proto.Auth.DeleteUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_BY_ID = 0;
  private static final int METHODID_CREATE_USER = 1;
  private static final int METHODID_EDIT_USER = 2;
  private static final int METHODID_DELETE_USER = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_USER_BY_ID:
          serviceImpl.getUserById((com.idm.proto.Auth.GetUserRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.GetUserResponse>) responseObserver);
          break;
        case METHODID_CREATE_USER:
          serviceImpl.createUser((com.idm.proto.Auth.CreateUserRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.CreateUserResponse>) responseObserver);
          break;
        case METHODID_EDIT_USER:
          serviceImpl.editUser((com.idm.proto.Auth.EditUserRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.EditUserResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((com.idm.proto.Auth.DeleteUserRequest) request,
              (io.grpc.stub.StreamObserver<com.idm.proto.Auth.DeleteUserResponse>) responseObserver);
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

  private static abstract class UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.idm.proto.Auth.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserService");
    }
  }

  private static final class UserServiceFileDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier {
    UserServiceFileDescriptorSupplier() {}
  }

  private static final class UserServiceMethodDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
              .addMethod(getGetUserByIdMethod())
              .addMethod(getCreateUserMethod())
              .addMethod(getEditUserMethod())
              .addMethod(getDeleteUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
