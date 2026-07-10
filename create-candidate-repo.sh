#!/bin/bash

# Creates a new candidate repository by pushing master and feature/user-service
# branches with shared history. This avoids GitHub's template feature which
# breaks branch relationships.
#
# Usage: ./create-candidate-repo.sh <repo-url>
# Example: ./create-candidate-repo.sh https://github.com/JavierMF/simple-user-service-123456.git

set -e

REPO_URL=$1

if [ -z "$REPO_URL" ]; then
    echo "Usage: $0 <repo-url>"
    echo "Example: $0 https://github.com/JavierMF/simple-user-service-123456.git"
    exit 1
fi

REMOTE_NAME="candidate-$(date +%s)"

echo "Adding remote: $REMOTE_NAME -> $REPO_URL"
git remote add "$REMOTE_NAME" "$REPO_URL"

echo "Pushing master..."
git push --force "$REMOTE_NAME" master

echo "Pushing feature/user-service..."
git push --force "$REMOTE_NAME" feature/user-service

echo "Cleaning up remote..."
git remote remove "$REMOTE_NAME"

echo "Done! Create the PR at: ${REPO_URL%.git}/compare/master...feature/user-service"
