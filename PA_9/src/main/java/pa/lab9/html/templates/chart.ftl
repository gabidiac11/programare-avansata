<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title> Pa Database </title>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">
    <header>${chartTitle}</header>
    <#list movieList as movie>
        </br>
        </br>

        <div class="container">
            <div class="list-group-item justify-content-between">
                MOVIE: <b>${movie.title}' | ID: ${movie.movieId} </b>
            </div>
            <div class="list-group-item justify-content-between">
                Genres:
                <div class="col-md-1">
                    <#list movie.genreEntities as genre>
                        <span class="badge badge-default badge-pill">${genre.name}</span>
                    </#list >
                </div>
            </div>
            <div class="list-group-item justify-content-between">
                Rating: <b>${movie.score} </b>
            </div>
            <div class="list-group-item justify-content-between">
                Release date: <b>${movie.releaseDate} </b>
            </div>
        </div>

    </#list>
</div>
</body>
</html>