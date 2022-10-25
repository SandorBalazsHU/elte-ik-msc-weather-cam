
lines=$(git diff --name-only main..dev | wc -l )
if [ $lines -gt 0 ]; then
    git push https://github.com/SandorBalazsHU/elte-ik-msc-weather-cam
fi