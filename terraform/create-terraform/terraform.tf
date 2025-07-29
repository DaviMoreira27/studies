terraform {
  required_providers {
    # Its possible to declare multiple providers, that enables creating a multi-platform archicteture
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.92"
    }
  }

  required_version = ">= 1.2"
}
