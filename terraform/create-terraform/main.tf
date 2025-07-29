provider "aws" { # It should be the same that it is state in the terraform.tf, required_providers block
  region = "us-east-1"
}

# AWS_AMI -> Amazon Image Machine, is an image that provides the software that is required to set up and boot an Amazon EC2 instance
# Data blocks serve the purpose of querying cloud providers for information about some resource
data "aws_ami" "chad-image" {
  most_recent = true

  # This datasource fetches the aws_ami that matches with this given filter
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*"]
  }

  # This owners property defines from which vendors I want to retrieve the AMI, it ensures confiability
  # https://documentation.ubuntu.com/aws/aws-how-to/instances/find-ubuntu-images/
  owners = ["099720109477"] # Canonical
}

# A resource block defines a component of your infraestructure
# This one defines an EC2 instance with the name "chad-instance"
#! IMPORTANT: The name provided in the line resource has no affect in the infraestructure itself,
#! His only purpose is to identity this resource inside its module (this file in the moment)
resource "aws_instance" "chad-instance" {
  ami           = data.aws_ami.chad-image.id # Retrieving the AMI ID
  instance_type = "t2.micro"

  tags = {
    Name = "test-rename"
    type = "Test"
  }
}
