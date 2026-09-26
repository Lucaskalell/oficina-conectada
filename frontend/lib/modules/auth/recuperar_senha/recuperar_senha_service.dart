import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:oficina_conectada_front/core/constants/api_constants.dart';

class RecuperarSenhaService {
  Future<void> solicitarToken(String email) async {
    final resposta = await http.post(
      Uri.parse('${ApiConstants.baseUrl}/auth/solicitar-redefinicao'),
      headers: {'Content-Type': 'application/json; charset=UTF-8'},
      body: jsonEncode({'email': email}),
    );

    if (resposta.statusCode != 202) {
      throw Exception('Não foi possível solicitar a redefinição. Tente novamente.');
    }
  }

  Future<void> redefinirSenha(String token, String novaSenha) async {
    final resposta = await http.post(
      Uri.parse('${ApiConstants.baseUrl}/auth/redefinir-senha'),
      headers: {'Content-Type': 'application/json; charset=UTF-8'},
      body: jsonEncode({'token': token, 'novaSenha': novaSenha}),
    );

    if (resposta.statusCode != 204) {
      final corpo = json.decode(resposta.body);
      throw Exception(corpo['mensagem'] ?? 'Código inválido ou expirado');
    }
  }
}
