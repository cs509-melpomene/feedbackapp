<html>

<head>
	<title>Feedback Application</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<link rel="stylesheet" href="mainGrid.css"/>
	<link rel="icon" type="image/png" href="favicon.png"/>
	<link rel="stylesheet" href="admin.css"/>
</head>

<body>
    <div class="row" style="padding: 50px;border-style: solid;">
	    <div class="col-md-4" style="border-style: solid;">
	        <div class ="row" style="border-style: none none solid none;padding: 10px;text-align: center;">
                <h5 style="text-align: center;">Delete snippets more than <i>n</i> years old</h5>
            </div>
            <div class ="row" style="padding: 10px;">
                <input type="text" id="numberDays" value="n" style="text-align: center;width: 60%;"><label>&nbsp;&nbsp;&nbsp;Days Old</label>
                <table style="border-style:solid;width: 100%; margin-top: 5px;">
                  <tr style="border-style:solid;">
                    <th style="border-style:solid;">Snippet ID</th>
                    <th style="border-style:solid;">Snippet Time</th>
                  </tr>
                  <!-- for loop to display snippets -->
                  <?php $Snippetid = array("459d67bfc77afc82bec3" => "09-28-2020 1:23pm", "559d67bfc77afc82bec4" => "10-28-2020 1:33pm", "659d67bfc77afc82bec5" => "03-23-2020 11:09pm", "759d67bfc77afc82bec6" => "11-19-2020 3:00pm"); ?>
                  <?php foreach($Snippetid as $id => $date): ?>
                      <tr>
                          <td style="border-style:none solid  none none;"><?php echo $id; ?></td>
                          <td><?php echo $date; ?></td>
                      </tr>
                  <?php endforeach; ?>
                </table>
            </div>
            <div class="row">
                <input type="button" value="Confirm" style="text-align: center;margin: auto; width: 50%;padding: 10px; margin-bottom: 5px;">
            </div>
        </div>
        <div class="col-md-8">
            <table style="border-style:solid; width: 100%;">
                <tr style="border-style:solid;">
                    <th style="border-style:solid;">Snippet ID</th>
                    <th style="border-style:solid;">Snippet Time</th>
                    <th style="border-style:solid;">Click to Delete</th>
                </tr>
                <!-- for loop to display snippets -->
                <?php $Snippetid = array("459d67bfc77afc82bec3" => "09-28-2020 1:23pm", "559d67bfc77afc82bec4" => "10-28-2020 1:33pm", "659d67bfc77afc82bec5" => "03-23-2020 11:09pm", "759d67bfc77afc82bec6" => "11-19-2020 3:00pm"); ?>
                <?php foreach($Snippetid as $id => $date): ?>
                  <tr>
                        <td style="border-style:none solid  none none;"><?php echo $id; ?></td>
                        <td style="border-style:none solid  none none;"><?php echo $date; ?></td>
                        <td><input type="button" value="Delete" style="text-align: center; width: 100%;"></td>
                  </tr>
                <?php endforeach; ?>
             </table>
       </div>
    </div>
</body>

</html>
