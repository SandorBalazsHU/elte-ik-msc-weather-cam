#!/bin/bash
set +e  # Grep succeeds with nonzero exit codes to show results.
git status | grep modified
if [ $? -eq 0 ]
then
    set -e
    git push https://github.com/SandorBalazsHU/elte-ik-msc-weather-cam 
else
    set -e
    echo "No changes since last run"
fi

