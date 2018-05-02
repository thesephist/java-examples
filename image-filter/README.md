# image-filter

Simple image filters using Java AWT's `Color` and 2D arrays to store pixel information.

## Synopsis

**image-filter** allows you to manipulate common image files with filters to change color, saturation, contrast, and move and stretch the images around to apply "filters" to them.

## Relevant concepts

This project explores how image and color information is stored on computers by addressing images with pixel positions and RGB color values, and modifying them through filters.

### Language features

- Arrays and `ArrayList`s specifically, for dealing with pixel position.
- Multi-dimensional arrays and lists, since an image is a 2D data structure; nested (2D) for loops
- `try`/`catch` structures wrapping around fallible IO operations
- Static methods and their use case (`Filter` class)

### Libraries

- ArrayLists
- Color and Images
- Basic file system I/O, for reading, saving, and creating image files

### Patterns

- Overloading constructors for different purposes (in `ImageManager`)

## Room for expansion

There's lots of room for creativity in deciding other filters to add. The repository is provided out of the box with basic color adjustment filters like `saturate()`, `contrasty()`, `red()`, `greyscale()`, etc. and basic geometric operations like rotation, translation, and scaling, but the structure is simple enough to allow quick exploration.

Possible expansions might include loop up tables, partial filters, overlaying two images into one, or implementing blend modes when merging two images.

