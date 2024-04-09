<?php
  include 'pdoDbConnection.php';
  
  
    $pdo = Database::connect();
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = 'SELECT status FROM Hall_Effect_Sensor WHERE id = (SELECT MAX(id) FROM Hall_Effect_Sensor)';
    
    $q = $pdo->prepare($sql);
    $q->execute(array($id));
    $data = $q->fetch(PDO::FETCH_ASSOC);
    Database::disconnect();
    
    echo $data['status'];
  
?>