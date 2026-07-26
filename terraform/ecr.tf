resource "aws_ecr_repository" "java_webapp" {
  name                 = "java-webapp"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "java-webapp-ecr"
  }
}