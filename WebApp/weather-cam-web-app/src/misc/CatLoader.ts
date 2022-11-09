class AspectImage extends Image {
  aspect: number | undefined;
}
const catPreloadImage = new AspectImage();
const catApiUrl = "https://genrandom.com/api/cat";

export function loadCats(url: string = catApiUrl) {
  catPreloadImage.src = url;
}

export default catPreloadImage;
