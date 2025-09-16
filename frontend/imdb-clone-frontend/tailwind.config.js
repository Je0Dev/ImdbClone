/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      // Add our custom font to Tailwind's font families
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      },
      // Add custom colors for our brand
      colors: {
        'brand-yellow': '#f5c518',
        'brand-dark': '#121212',
        'brand-light-dark': '#212121',
      },
    },
  },
  plugins: [],
}
