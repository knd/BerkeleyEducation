#!/bin/bash
#PBS -N CS61C
#PBS -V
#PBS -l nodes=1
#PBS -q batch
cd $PBS_O_WORKDIR
# workaround to fix a thread affinity problem in Linux
export GOTOBLAS_MAIN_FREE=1
# name of the file to execute
./bench-openmp

