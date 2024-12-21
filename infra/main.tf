# provider
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.75.0"
    }
  }
}

provider "aws" {
  region = var.region_name
}

output "instance_ec2" {
  value = aws_instance.backend_instance_piba.public_ip
}
