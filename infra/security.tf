data "aws_iam_policy_document" "document_policy_ec2_piba" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ec2.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "role_ec2_piba" {
  name               = local.name_role_ec2_piba
  assume_role_policy = data.aws_iam_policy_document.document_policy_ec2_piba.json

  tags = {
    Name    = local.name_role_ec2_piba,
    project = local.tag_project
  }
}

resource "aws_iam_role_policy_attachment" "ec2_policy_role_s3_access_piba" {
  role       = aws_iam_role.role_ec2_piba.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess"
}

resource "aws_iam_role_policy_attachment" "ec2_amazon_ssm_piba" {
  role       = aws_iam_role.role_ec2_piba.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}


/*data "aws_iam_policy_document" "assume_role_gtw_piba" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["apigateway.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "role_api_gtw_cloudwatch_piba" {
  name               = local.name_role_api_gtw_cw
  assume_role_policy = data.aws_iam_policy_document.assume_role_gtw_piba.json

  tags = {
    Name    = local.name_role_api_gtw_cw,
    project = local.tag_project
  }
}

data "aws_iam_policy_document" "assume_role_lambda_piba" {
  statement {
    effect  = "Allow"
    actions = [
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:DescribeLogGroups",
      "logs:DescribeLogStreams",
      "logs:PutLogEvents",
      "logs:GetLogEvents",
      "logs:FilterLogEvents"
    ]
    resources = [
      aws_cloudwatch_log_group.log_api_gtw.arn,
      "${aws_cloudwatch_log_group.log_api_gtw.arn}/*"
    ]
  }

  depends_on = [aws_cloudwatch_log_group.log_api_gtw]
}

resource "aws_iam_policy" "policy_api_gateway_cloudwatch_piba" {
  name   = local.name_policy_api_gtw_cw
  policy = data.aws_iam_policy_document.assume_role_lambda_piba.json

  depends_on = [aws_cloudwatch_log_group.log_api_gtw]
}

resource "aws_iam_role_policy_attachment" "attach_role_api_gateway_cloudwatch_piba" {
  role       = aws_iam_role.role_api_gtw_cloudwatch_piba.name
  policy_arn = aws_iam_policy.policy_api_gateway_cloudwatch_piba.arn
}*/

/*resource "aws_iam_role_policy_attachment" "api_gateway_cloudwatch" {
  role       = aws_iam_role.role_api_gtw_cloudwatch_piba.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonAPIGatewayPushToCloudWatchLogs"
}*/

/*resource "aws_s3_bucket_policy" "lb_logs_s3_piba" {
  bucket = aws_s3_bucket.s3_log_lb_piba.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          AWS = "arn:aws:iam::${data.aws_elb_service_account.main.id}:root"
        }
        Action = "s3:PutObject"
        Resource = [
          "${aws_s3_bucket.s3_log_lb_piba.arn}/*",
          aws_s3_bucket.s3_log_lb_piba.arn
        ]
      }
    ]
  })
}*/
