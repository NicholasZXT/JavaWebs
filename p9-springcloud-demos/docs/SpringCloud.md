
项目结构
```text
cloud-demo/
├── pom.xml                ← 顶层父 POM（定义版本、BOM、公共依赖）
├── commons/               ← 工具类、DTO、异常封装、Starter 等共享代码（jar，非服务）
├── gateway/               ← 接入层：Spring Cloud Gateway + 认证/限流
├── service-common/        ← 预留公共服务（如 auth、notify）
├── service-core/          ← 聚合核心业务服务（非可运行模块，仅用于组织子模块）
│   ├── user/
│   ├── product/
│   └── order/
└── docs/                  ← 非 Maven 模块，存放文档
```