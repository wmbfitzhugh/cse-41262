import lib.DataPrep


def moviesPrep = new DataPrep(
    srcDir: "/adjust/to/your/dir",
    srcFile: "Movie_Reviews"
)
moviesPrep.prep()
