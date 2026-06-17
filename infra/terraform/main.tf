provider "aws" {
  region = var.aws_region
}

resource "aws_s3_bucket" "supplier_documents" {
  bucket = var.bucket_name
}

resource "aws_iam_role" "ec2_s3_role" {
  name = "SupplierDocumentPlatformEC2Role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_iam_policy" "s3_policy" {
  name = "SupplierDocumentPlatformS3Policy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid    = "SupplierDocumentPlatformS3ObjectAccess"
        Effect = "Allow"
        Action = [
          "s3:PutObject",
          "s3:GetObject",
          "s3:DeleteObject"
        ]
        Resource = "${aws_s3_bucket.supplier_documents.arn}/*"
      },
      {
        Sid    = "SupplierDocumentPlatformS3ListBucket"
        Effect = "Allow"
        Action = [
          "s3:ListBucket"
        ]
        Resource = aws_s3_bucket.supplier_documents.arn
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "attach_s3_policy" {
  role       = aws_iam_role.ec2_s3_role.name
  policy_arn = aws_iam_policy.s3_policy.arn
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "SupplierDocumentPlatformEC2InstanceProfile"
  role = aws_iam_role.ec2_s3_role.name
}