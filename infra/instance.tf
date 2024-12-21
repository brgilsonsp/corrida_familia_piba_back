resource "aws_iam_instance_profile" "ec2_instance_profile_piba" {
  name = local.profile_ec2
  role = aws_iam_role.role_ec2_piba.name
}

resource "aws_instance" "backend_instance_piba" {
  ami                         = var.identity_ami
  instance_type               = var.instance_type
  iam_instance_profile        = aws_iam_instance_profile.ec2_instance_profile_piba.name
  subnet_id                   = aws_subnet.this.id
  associate_public_ip_address = true
  vpc_security_group_ids      = [aws_security_group.ec2_sg_piba.id]

  user_data = <<-EOF
                #!/bin/bash
                sudo yum update -y
                sudo yum install java-21-amazon-corretto-headless -y
                mkdir -p ~/app
                aws s3 cp s3://${var.bucket_name_s3_jar_file}/${var.path_jar_file_s3}-${var.version_app}/${var.jar_name} ~/app/
                aws s3 cp s3://${var.bucket_name_s3_jar_file}/${var.script_path_startup}/${var.script_name_startup} ~/app/
                cd ~/app
                chmod +x ./${var.script_name_startup}
                ./${var.script_name_startup}
              EOF

  root_block_device {
    volume_size = 10
    volume_type = "gp2"
  }

  tags = {
    Name    = local.name_server_ec2,
    project = local.project_name
  }
}

resource "aws_security_group" "ec2_sg_piba" {
  name        = local.name_sg
  description = "Configure access to server"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = local.port_api_backend
    to_port     = local.port_api_backend
    protocol    = local.protocol_tcp
    cidr_blocks = [local.cidr_internet]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = local.protocol_tcp
    cidr_blocks = [local.cidr_internet]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = [local.cidr_internet]
  }
}