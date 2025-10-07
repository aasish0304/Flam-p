const img = document.getElementById("frame");
const stats = document.getElementById("stats");
const toggleBtn = document.getElementById("toggleBtn");

// Default images (make sure both exist in /web folder)
let rawImage = "raw_frame.png";          // placeholder raw image
let processedImage = "processed_frame.png"; // edge-detected output
let showingProcessed = true;

toggleBtn.addEventListener("click", () => {
    if (showingProcessed) {
        img.src = rawImage;
        toggleBtn.textContent = "Show Processed";
        showingProcessed = false;
    } else {
        img.src = processedImage;
        toggleBtn.textContent = "Show Raw";
        showingProcessed = true;
    }
});

// FPS + resolution (could be dynamic later)
let fps = 15;
let resolution = "640x480";
stats.textContent = `FPS: ${fps}, Resolution: ${resolution}`;
