variable "region_name" {
  description = "Region AWS where resources will be create"
  type        = string
}

variable "identity_ami" {
  description = "ID AMI for used to instance EC2"
  type        = string
  default     = "ami-063d43db0594b521b"
}

variable "instance_type" {
  description = "Instancetype to instance EC2"
  type        = string
  default     = "t2.micro"
}

variable "version_app" {
  description = "Version of application"
  type        = string
}

variable "jar_name" {
  description = "Name of jar file"
  type        = string
  default     = "sporting-event-race.jar"
}

variable "vpc_id" {
  description = "VPC ID to user"
  type        = string
}

variable "bucket_name_s3_jar_file" {
  description = "Bucket name from JAR file"
  type        = string
}

variable "path_jar_file_s3" {
  description = "Path from JAR file"
  type        = string
}

