# java-starter-service

A Spring Boot 3.1.5 REST API service template for building production-ready Java applications.

## Features

- Spring Boot 3.1.5 with Java 17
- RESTful API with health check endpoints
- Automated Jira integration via GitHub Actions
- Maven build system

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Building the Project

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Jira Integration

This repository includes automated Jira integration through GitHub Actions. When you create a pull request, the workflow will automatically extract the Jira issue ID and update the issue status.

### How It Works

1. **Issue ID Extraction**: The workflow searches for Jira issue IDs in the following order:
   - First, checks the **branch name** (e.g., `PROJ-123-feature-name`, `ABC-456-bugfix`)
   - If not found, checks the **PR title** (e.g., `[PROJ-123] Add new feature`)
   - If not found, checks the **PR description** (e.g., `This PR addresses PROJ-123`)
2. **Pull Request Opened**: When you open a PR, the workflow extracts the issue ID and updates the Jira status to "In Review"
3. **Pull Request Merged**: When the PR is merged to `main`, the workflow updates the Jira status to "Done"
4. **No Issue ID**: If no issue ID is found in any location, the workflow gracefully skips Jira updates

### Supported Issue ID Patterns

The workflow automatically detects Jira issue IDs matching the pattern `[A-Z]+-[0-9]+` from:

**Branch names:**
- `PROJ-123-feature-description`
- `ABC-456/bugfix`
- `feature/JIRA-789-implementation`

**PR titles:**
- `[PROJ-123] Add new feature`
- `PROJ-123: Fix critical bug`
- `Implement feature for TEAM-456`

**PR descriptions:**
- Any text containing issue IDs like `PROJ-123`, `ABC-456`, etc.

### Required GitHub Secrets

To enable Jira integration, configure the following secrets in your repository settings (Settings → Secrets and variables → Actions):

| Secret Name | Description | Example |
|------------|-------------|---------|
| `JIRA_BASE_URL` | Your Jira instance URL (without trailing slash) | `https://your-domain.atlassian.net` |
| `JIRA_EMAIL` | Email address of the Jira user | `your-email@example.com` |
| `JIRA_API_TOKEN` | Jira API token for authentication | Generate at: https://id.atlassian.com/manage-profile/security/api-tokens |

### Generating a Jira API Token

1. Go to https://id.atlassian.com/manage-profile/security/api-tokens
2. Click "Create API token"
3. Give it a label (e.g., "GitHub Actions")
4. Copy the token and add it as a GitHub secret

### Jira Status Names

The workflow looks for transitions named:
- **"In Review"** (case-insensitive) - Set when PR is opened/updated
- **"Done"** (case-insensitive) - Set when PR is merged to main

If these exact status names don't exist in your Jira workflow, you may need to customize the workflow file (`.github/workflows/jira-integration.yml`) to match your Jira workflow transitions.

### Error Handling

The workflow is designed to fail gracefully:
- If no issue ID is found in branch name, PR title, or PR description, it skips Jira updates
- If Jira credentials are not configured, it logs a message and continues
- If Jira API calls fail, the workflow continues without failing the CI/CD pipeline
- All Jira integration steps use `continue-on-error: true` to prevent blocking other workflows

## License

This project is licensed under the MIT License - see the LICENSE file for details.

