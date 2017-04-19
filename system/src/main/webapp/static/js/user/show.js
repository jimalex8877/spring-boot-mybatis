$( function () {
	$( '#reload' ).click( function () {
		getAllUserUrl += '';
		$.get( getAllUserUrl, function ( data ) {
			if ( data.status == 200 ) {
				var userList = data.data;
				$( '#tableTbody' ).html( '' );
				$.each( userList, function ( i, user ) {
					var html = "<tr><td>" + user.userName + "</td><td>" + user.age + "</td><td>" + user.sex
					html += "</td><td><button type='button' class='btn btn-danger' onclick='deleteUser(" + user.id
					html += ")'>删除</button><button type='button' class='btn btn-info' onclick='updateUser("
					html += user.id + ")'>编辑</button></td></tr>";
					$( '#tableTbody' ).append( html );
				} );
			} else {
				$( '#warningAlert span' ).text( "获取用户信息失败!" );
				$( '#warningAlert' ).removeClass( 'hide' );
				setTimeout( '$("#warningAlert").addClass("hide")', 5000 );
			}
		} );
	} );
	$( '#addUser' ).click( function () {

	} );
} );

function deleteUser( id ) {
	$( '#okModal' ).modal( {
		keyboard: true
	} ).on( 'hide.bs.modal',
		function () {
			$( "#okDelUser" ).unbind( "click" );
		} );
	$( '#okDelUser' ).bind( "click", function () {
		delUserUrl += id;
		$.ajax( {
			type: 'GET',
			url: delUserUrl,
			async: false,
			cache: false,
			dataType: 'json',
			success: function ( data ) {
				if ( data.status == 200 ) {
					$( '#successAlert span' ).text( data.message );
					$( '#successAlert' ).removeClass( 'hide' );
					setTimeout( '$("#successAlert").addClass("hide")', 5000 );
				} else {
					$( '#warningAlert span' ).text( data.message );
					$( '#warningAlert' ).removeClass( 'hide' );
					setTimeout( '$("#warningAlert").addClass("hide")', 5000 );
				}
				$( '#reload' ).click();
			}
		} );
		$( '#okModal' ).modal( 'hide' );
	} );
}

function updateUser( id ) {

}