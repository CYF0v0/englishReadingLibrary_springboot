# 课程管理系统

## 项目概述

本项目是一个基于Spring Boot的课程管理系统，旨在提供课程的创建、查询、更新和删除功能。系统支持学生报名课程，并具备检测重复报名的功能。

## 技术栈

- **Java**：项目的主要编程语言。
- **Spring Boot**：用于快速构建基于Spring的应用程序。
- **Maven**：项目构建工具。
- **Postman**：用于API测试。

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── login/
│   │               ├── controller/
│   │               │   └── CourseController.java
│   │               ├── model/
│   │               │   └── Course.java
│   │               ├── service/
│   │               │   └── CourseService.java
│   │               └── EnrollCourse.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── example/
                └── login/
                    └── CourseControllerTest.java
```

## 功能模块

### 1. 课程管理

- **创建课程**：通过POST请求创建新的课程。
  - URL：`/api/courses/create`
  - 请求体示例：
    ```json
    {
      "courseName": "Math",
      "description": "Introduction to Mathematics"
    }
    ```

- **查询所有课程**：通过GET请求获取所有课程。
  - URL：`/api/courses/getAll`

- **根据ID查询课程**：通过GET请求根据课程ID查询课程。
  - URL：`/api/courses/getById/{id}`

- **根据名称查询课程**：通过GET请求根据课程名称查询课程。
  - URL：`/api/courses/getByName/{name}`

- **根据ID删除课程**：通过DELETE请求根据课程ID删除课程。
  - URL：`/api/courses/deleteById/{id}`

- **根据名称删除课程**：通过DELETE请求根据课程名称删除课程。
  - URL：`/api/courses/deleteByName/{name}`

- **根据ID更新课程**：通过PUT请求根据课程ID更新课程信息。
  - URL：`/api/courses/updateById/{id}`
  - 请求体示例：
    ```json
    {
      "courseName": "Updated Math",
      "description": "Advanced Mathematics"
    }
    ```

- **根据名称更新课程**：通过PUT请求根据课程名称更新课程信息。
  - URL：`/api/courses/updateByName/{name}`
  - 请求体示例：
    ```json
    {
      "courseName": "Updated Math",
      "description": "Advanced Mathematics"
    }
    ```

### 2. 学生报名管理

- **创建报名**：通过POST请求创建学生报名课程。
  - URL：`/api/enrollcourses/create`
  - 请求体示例：
    ```json
    {
      "courseName": "Math",
      "studentId": 1,
      "date": "2023-11-23",
      "status": true,
      "score": 90
    }
    ```
  - **重复报名检测**：系统会自动检测学生是否已经报名该课程，如果重复报名，将返回409 Conflict状态码和错误信息。

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/yourusername/course-management-system.git
cd course-management-system
```

### 2. 构建项目

```bash
mvn clean install
```

### 3. 运行项目

```bash
mvn spring-boot:run
```

### 4. 使用Postman测试

1. 启动Postman。
2. 创建一个新的请求。
3. 设置请求类型和URL。
4. 设置请求体（JSON格式）。
5. 点击`Send`按钮发送请求。
6. 查看响应状态码和响应体。

## 错误处理

- **404 Not Found**：当请求的资源不存在时返回。
- **409 Conflict**：当检测到重复报名时返回。
- **500 Internal Server Error**：当发生服务器内部错误时返回。

## 贡献

欢迎提交Issue和Pull Request。

## 许可证

本项目采用MIT许可证，详情请参阅`LICENSE`文件。

---

通过以上文档，开发者和用户可以快速了解项目的基本信息、功能模块、快速开始步骤以及错误处理方式。