resource "aws_vpc" "vpc_piba" {
  cidr_block = local.cidr_vpc
  tags = {
    Name    = local.name_vpc,
    project = local.tag_project
  }
}

resource "aws_subnet" "subnet_piba" {
  vpc_id                  = aws_vpc.vpc_piba.id
  cidr_block              = local.cidr_servers
  availability_zone       = "${var.region_name}a"
  map_public_ip_on_launch = true # Caso a instância precise de IP público para atualizações ou acesso externo
  tags = {
    Name    = local.name_subnet,
    project = local.tag_project
  }
}

resource "aws_internet_gateway" "igw_piba" {
  vpc_id = aws_vpc.vpc_piba.id
  tags = {
    Name    = local.name_igw,
    project = local.tag_project
  }
}

resource "aws_route_table" "route_table_piba" {
  vpc_id = aws_vpc.vpc_piba.id
  route {
    cidr_block = local.cidr_internet
    gateway_id = aws_internet_gateway.igw_piba.id
  }
  tags = {
    Name    = local.name_route_table,
    project = local.tag_project
  }
}

resource "aws_route_table_association" "route_association_piba" {
  subnet_id      = aws_subnet.subnet_piba.id
  route_table_id = aws_route_table.route_table_piba.id
}

resource "aws_security_group" "ec2_sg_piba" {
  name        = local.name_sg
  description = "Permite acesso na porta 9111"
  vpc_id      = aws_vpc.vpc_piba.id

  ingress {
    from_port   = local.port_api_backend
    to_port     = local.port_api_backend
    protocol    = "tcp"
    cidr_blocks = [local.cidr_internet]
    #cidr_blocks = [aws_vpc.vpc_piba.cidr_block]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [local.cidr_internet]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = [local.cidr_internet]
  }

  depends_on = [aws_vpc.vpc_piba]
}