const img = document.getElementById("frame") as HTMLImageElement;
const stats = document.getElementById("stats") as HTMLParagraphElement;

// load dummy base64 image
img.src = "processed_frame.png"; // exported from Android

let fps = 15;
let resolution = "640x480";
stats.innerText = `FPS: ${fps}, Resolution: ${resolution}`;
