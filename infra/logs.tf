/*resource "aws_cloudwatch_log_group" "log_api_gtw" {
  name              = local.name_cw_api
  retention_in_days = 1
  skip_destroy      = false

  tags = {
    Name    = local.name_cw_api,
    project = local.tag_project
  }
}

resource "aws_s3_bucket" "s3_log_lb_piba" {
  bucket        = "${local.name_s3_log_lb}-${random_string.random_string_piba.result}"
  force_destroy = true

  tags = {
    Name    = "${local.name_s3_log_lb}-${random_string.random_string_piba.result}",
    project = local.tag_project
  }
}

resource "aws_s3_bucket_public_access_block" "s3_access_log_lb_piba" {
  bucket = aws_s3_bucket.s3_log_lb_piba.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}*/