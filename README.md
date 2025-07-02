# 🛠️ Orquestração dos Microsserviços com Makefile + Docker Compose

Este projeto utiliza **Docker Compose** para orquestrar múltiplos microsserviços. Para facilitar o uso no dia a dia, usamos um **Makefile** que centraliza os comandos de build e execução.

## Pré-requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Make](https://www.gnu.org/software/make/) instalado no sistema
    - No **Windows**, sugerimos usar o [WSL](https://learn.microsoft.com/pt-br/windows/wsl/) (Windows Subsystem for Linux).
    - Também é possível usar o [Gow](https://github.com/bmatzelle/gow) ou [GnuWin](http://gnuwin32.sourceforge.net/packages/make.htm), ou instalar o Make pelo gerenciador de pacotes do seu terminal.

## Comandos principais

```sh
# Subir TODOS os microsserviços e bancos de dados
make up

# Subir TODOS os microsserviços e bancos de dados (builda as imagens antes)
make build

# Derrubar TODOS os containers da aplicação
make down

# Limpar imagens e volumes (cuidado!)
make clean
