resource "aws_iam_instance_profile" "ec2_instance_profile_piba" {
  name = "ec2_instance_profile_piba"
  role = aws_iam_role.role_ec2_piba.name
}

resource "aws_instance" "backend_instance_piba" {
  ami                         = var.identity_ami
  instance_type               = "t2.micro"
  iam_instance_profile        = aws_iam_instance_profile.ec2_instance_profile_piba.name
  subnet_id                   = aws_subnet.subnet_piba.id
  associate_public_ip_address = true
  vpc_security_group_ids      = [aws_security_group.ec2_sg_piba.id]

  depends_on = [aws_vpc.vpc_piba, aws_subnet.subnet_piba]

  user_data = <<-EOF
                #!/bin/bash
                sudo yum update -y
                sudo yum install java-21-amazon-corretto-headless -y
                mkdir -p ~/app
                aws s3 cp ${var.s3_path_jar_file}/${var.jar_name} ~/app/
                cd ~/app
                nohup java -jar ${var.jar_name} &
              EOF

  root_block_device {
    volume_size = 10
    volume_type = "gp2"
  }

  tags = {
    Name    = local.name_ec2,
    project = local.tag_project
  }
}

/*resource "aws_lb" "lb-piba" {
  name               = local.name_lb
  internal           = false
  load_balancer_type = "network"
  subnets            = [aws_subnet.subnet_piba.id]

  access_logs {
    bucket  = aws_s3_bucket.s3_log_lb_piba.id
    enabled = true
  }

  depends_on = [
    aws_s3_bucket.s3_log_lb_piba,
    aws_s3_bucket_policy.lb_logs_s3_piba
  ]

  tags = {
    Name    = local.name_lb,
    project = local.tag_project
  }
}

resource "aws_lb_target_group" "target-group-piba" {
  name        = local.name_tg
  port        = local.port_api_backend
  protocol    = "TCP"
  target_type = "instance"
  vpc_id      = aws_vpc.vpc_piba.id

  depends_on = [aws_vpc.vpc_piba]

  tags = {
    Name    = local.name_tg,
    project = local.tag_project
  }
}

resource "aws_lb_target_group_attachment" "lb-target-group-piba" {
  target_group_arn = aws_lb_target_group.target-group-piba.arn
  target_id        = aws_instance.backend_instance_piba.id
  port             = local.port_api_backend

  depends_on = [aws_instance.backend_instance_piba]
}

resource "aws_lb_listener" "lb-listener-piba" {
  load_balancer_arn = aws_lb.lb-piba.arn
  port              = local.port_api_backend
  protocol          = "TCP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.target-group-piba.arn
  }

  tags = {
    Name    = local.name_lb_listener,
    project = local.tag_project
  }
}*/
