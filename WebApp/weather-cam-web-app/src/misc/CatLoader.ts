const catPreloadImage = new Image();
const catApiUrl = "https://genrandom.com/api/cat";

export function loadCats(url: string = catApiUrl) {
  catPreloadImage.src = url;
}

export default catPreloadImage;
