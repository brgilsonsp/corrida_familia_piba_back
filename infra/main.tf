# provider
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.75.0"
    }
    /*random = {
      source  = "hashicorp/random"
      version = "3.6.3"
    }*/
  }
}

provider "aws" {
  region = var.region_name
}

/*resource "random_string" "random_string_piba" {
  length  = 16
  special = false
  upper   = false
}

data "aws_elb_service_account" "main" {}

output "nbl_dns_name" {
  value = aws_lb.lb-piba.dns_name
}*/

output "instance_ec2" {
  value = aws_instance.backend_instance_piba.public_ip
}