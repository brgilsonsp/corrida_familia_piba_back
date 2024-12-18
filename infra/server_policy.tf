data "aws_iam_policy_document" "assume_role_ec2" {
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
  assume_role_policy = data.aws_iam_policy_document.assume_role_ec2.json

  tags = {
    Name    = local.name_role_ec2_piba,
    project = local.project_name
  }
}

data "aws_iam_policy_document" "s3_read_only" {
  statement {
    sid    = "S3ReadOnlyEc2Piba"
    effect = "Allow"
    actions = [
      "s3:Get*",
      "s3:List*",
      "s3:Describe*",
    ]
    resources = [
      "arn:aws:s3:::${var.bucket_name_s3_jar_file}",
      "arn:aws:s3:::${var.bucket_name_s3_jar_file}/*"
    ]
  }
}

resource "aws_iam_policy" "policy_s3_read_only" {
  name        = local.name_policy_ec2_piba
  description = "Policy to access file in S3"
  policy      = data.aws_iam_policy_document.s3_read_only.json

  tags = {
    Name    = local.name_policy_ec2_piba,
    project = local.project_name
  }
}

resource "aws_iam_role_policy_attachment" "ec2_policy_role_s3_access_piba" {
  role       = aws_iam_role.role_ec2_piba.name
  policy_arn = aws_iam_policy.policy_s3_read_only.arn
}