variable "region_name" {
  description = "Region AWS where resources will be create"
  type        = string
  default     = "us-east-1"
}

variable "identity_ami" {
  description = "ID AMI for used to instance EC2"
  type        = string
  default     = "ami-063d43db0594b521b"
}

variable "s3_path_jar_file" {
  description = "Instance type for EC2"
  type        = string
  default     = "s3://artifactory-gilson-souza/java"
}

variable "jar_name" {
  description = "Name of jar file"
  type        = string
  default     = "sporting-event-race.jar"
}
