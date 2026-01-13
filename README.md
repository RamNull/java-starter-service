# java-starter-service

A Spring Boot 3.1.5 REST API service template for building production-ready Java applications. This template includes automated code review capabilities to ensure code quality and consistency.

## 🚀 Features

- **Spring Boot 3.1.5** with Java 17
- **REST API** with health check endpoints
- **Automated Code Reviews** via GitHub Actions and CODEOWNERS
- **Maven** build system
- **Production-ready** starter template

## 🤖 Automated Code Review System

This repository includes a comprehensive automated code review system that:

- ✅ **Auto-assigns reviewers** using CODEOWNERS
- ✅ **Runs automated quality checks** on every pull request
- ✅ **Posts review checklists** automatically
- ✅ **Executes builds and tests** with detailed reporting
- ✅ **Performs static code analysis**
- ✅ **Provides standardized PR templates**

### How It Works

When you create a pull request:
1. **CODEOWNERS** automatically assigns reviewers
2. **PR Template** loads with a comprehensive checklist
3. **GitHub Actions** runs automated checks:
   - Maven build and test
   - Test report generation
   - Static code analysis
   - TODO/FIXME detection

📚 **Full Documentation:** See [`.github/AUTOMATED_REVIEW.md`](.github/AUTOMATED_REVIEW.md)

## 🛠️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

### Test

```bash
mvn test
```

## 📖 Contributing

1. Create a feature branch
2. Make your changes
3. Open a pull request
   - The PR template will auto-populate
   - Automated checks will run
   - Reviewers will be automatically assigned

See [`.github/AUTOMATED_REVIEW.md`](.github/AUTOMATED_REVIEW.md) for detailed contribution guidelines.

## 📋 Code Review Standards

This project follows comprehensive code review guidelines documented in:
- [`.github/copilot-instructions.md`](.github/copilot-instructions.md) - Detailed review standards
- [`.github/pull_request_template.md`](.github/pull_request_template.md) - PR checklist

## 📄 License

This project is licensed under the terms specified in the LICENSE file.

