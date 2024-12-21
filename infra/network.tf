resource "aws_subnet" "this" {
  vpc_id                  = var.vpc_id
  cidr_block              = local.cidr_servers
  availability_zone       = "${var.region_name}a"
  map_public_ip_on_launch = true # Caso a instância precise de IP público para atualizações ou acesso externo
  tags = {
    Name    = local.name_subnet,
    project = local.project_name
  }
}