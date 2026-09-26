import 'package:equatable/equatable.dart';

abstract class RecuperarSenhaState extends Equatable {
  const RecuperarSenhaState();

  @override
  List<Object?> get props => [];
}

class RecuperarSenhaInicial extends RecuperarSenhaState {}

class RecuperarSenhaCarregando extends RecuperarSenhaState {}

class TokenEnviado extends RecuperarSenhaState {}

class SenhaRedefinida extends RecuperarSenhaState {}

class RecuperarSenhaErro extends RecuperarSenhaState {
  final String mensagem;

  const RecuperarSenhaErro(this.mensagem);

  @override
  List<Object?> get props => [mensagem];
}
