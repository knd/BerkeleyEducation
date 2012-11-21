#include <stdio.h>

int main() {
  char *arg[2];
  arg[0] = "nguyen tan do";
  arg[1] = "pham thuy duong";
  execv("echo", arg);
}
