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
    <header>Movie playlist</header>

    <div class="container">
        <p>Graph representing each connection movies have with themselves (share at least one director)</p>
        <img style="width: 100%" src="matched-initial.png">
    </div>
    <div class="container">
        <p>Graph of which each edge corresponds to a day when you can watch 2 movies with the same director (and no other day shares that director)</p>
        <img style="width: 100%" src="matched-after.png">
    </div>

    <#list 0..moviePairList?size-1 as i>
        <p style="padding-top: 30px"> Day #${i+1} </p>
        <div style="display: flex; justify-content: space-between; width: 100%;">
            <#list moviePairList[i] as movie >
                <div class="container" style="flex: 1">
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
                        Directors:
                        <div class="col-md-1">
                            <#list movie.persons as person>
                                <span class="badge badge-default badge-pill">${person.name}</span>
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
    </#list>
</div>
</body>
</html>