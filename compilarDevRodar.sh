#!/bin/bash

./compilar.sh
if [[ "$?" -ne 0 ]]; then
    exit 1
fi

./rodar.sh