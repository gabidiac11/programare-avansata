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
    <nav class="navbar navbar-light bg-light">
        <span class="navbar-brand mb-0 h1">Movie playlist</span>
    </nav>
    <div style="display: flex; justify-content: space-between; width: 100%;">
        <div class="card" style="flex: 1">
            <img class="card-img-top" src="matched-initial.png" alt="Card image cap">
            <div class="card-body">
                <p class="card-text">Graph representing each connection movies have with themselves (share at least one director)</p>
            </div>
        </div>
        <div class="card" style="flex: 1">
            <img class="card-img-top" src="matched-after.png" alt="Card image cap">
            <div class="card-body">
                <p class="card-text">Graph of which each edge corresponds to a day when you can watch 2 movies with the same director (and no other day shares that director)</p>
            </div>
        </div>
    </div>

    <#list 0..moviePairList?size-1 as i>
        <div class="card" style="margin-top: 20px;">
            <h5 class="card-header">Day #${i+1}</h5>
            <div class="card-body">
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
            </div>
        </div>
    </#list>
</div>
</body>
</html>